import {
  entityTableSelector,
  entityDetailsButtonSelector,
  entityDetailsBackButtonSelector,
  entityCreateButtonSelector,
  entityCreateSaveButtonSelector,
  entityCreateCancelButtonSelector,
  entityEditButtonSelector,
  entityDeleteButtonSelector,
  entityConfirmDeleteButtonSelector,
} from '../../support/entity';

describe('MarketSegmentationTypeB2bAlt e2e test', () => {
  const marketSegmentationTypeB2bAltPageUrl = '/market-segmentation-type-b-2-b-alt';
  const marketSegmentationTypeB2bAltPageUrlPattern = new RegExp('/market-segmentation-type-b-2-b-alt(\\?.*)?$');
  const username = Cypress.env('E2E_USERNAME') ?? 'user';
  const password = Cypress.env('E2E_PASSWORD') ?? 'user';
  const marketSegmentationTypeB2bAltSample = { value: 'Soft interactive', language: 'GREEK' };

  let marketSegmentationTypeB2bAlt;

  beforeEach(() => {
    cy.login(username, password);
  });

  beforeEach(() => {
    cy.intercept('GET', '/api/market-segmentation-type-b-2-b-alts+(?*|)').as('entitiesRequest');
    cy.intercept('POST', '/api/market-segmentation-type-b-2-b-alts').as('postEntityRequest');
    cy.intercept('DELETE', '/api/market-segmentation-type-b-2-b-alts/*').as('deleteEntityRequest');
  });

  afterEach(() => {
    if (marketSegmentationTypeB2bAlt) {
      cy.authenticatedRequest({
        method: 'DELETE',
        url: `/api/market-segmentation-type-b-2-b-alts/${marketSegmentationTypeB2bAlt.id}`,
      }).then(() => {
        marketSegmentationTypeB2bAlt = undefined;
      });
    }
  });

  it('MarketSegmentationTypeB2bAlts menu should load MarketSegmentationTypeB2bAlts page', () => {
    cy.visit('/');
    cy.clickOnEntityMenuItem('market-segmentation-type-b-2-b-alt');
    cy.wait('@entitiesRequest').then(({ response }) => {
      if (response.body.length === 0) {
        cy.get(entityTableSelector).should('not.exist');
      } else {
        cy.get(entityTableSelector).should('exist');
      }
    });
    cy.getEntityHeading('MarketSegmentationTypeB2bAlt').should('exist');
    cy.url().should('match', marketSegmentationTypeB2bAltPageUrlPattern);
  });

  describe('MarketSegmentationTypeB2bAlt page', () => {
    describe('create button click', () => {
      beforeEach(() => {
        cy.visit(marketSegmentationTypeB2bAltPageUrl);
        cy.wait('@entitiesRequest');
      });

      it('should load create MarketSegmentationTypeB2bAlt page', () => {
        cy.get(entityCreateButtonSelector).click();
        cy.url().should('match', new RegExp('/market-segmentation-type-b-2-b-alt/new$'));
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bAlt');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bAltPageUrlPattern);
      });
    });

    describe('with existing value', () => {
      beforeEach(() => {
        cy.authenticatedRequest({
          method: 'POST',
          url: '/api/market-segmentation-type-b-2-b-alts',
          body: marketSegmentationTypeB2bAltSample,
        }).then(({ body }) => {
          marketSegmentationTypeB2bAlt = body;

          cy.intercept(
            {
              method: 'GET',
              url: '/api/market-segmentation-type-b-2-b-alts+(?*|)',
              times: 1,
            },
            {
              statusCode: 200,
              headers: {
                link: '<http://localhost/api/market-segmentation-type-b-2-b-alts?page=0&size=20>; rel="last",<http://localhost/api/market-segmentation-type-b-2-b-alts?page=0&size=20>; rel="first"',
              },
              body: [marketSegmentationTypeB2bAlt],
            }
          ).as('entitiesRequestInternal');
        });

        cy.visit(marketSegmentationTypeB2bAltPageUrl);

        cy.wait('@entitiesRequestInternal');
      });

      it('detail button click should load details MarketSegmentationTypeB2bAlt page', () => {
        cy.get(entityDetailsButtonSelector).first().click();
        cy.getEntityDetailsHeading('marketSegmentationTypeB2bAlt');
        cy.get(entityDetailsBackButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bAltPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2bAlt page and go back', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bAlt');
        cy.get(entityCreateSaveButtonSelector).should('exist');
        cy.get(entityCreateCancelButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bAltPageUrlPattern);
      });

      it('edit button click should load edit MarketSegmentationTypeB2bAlt page and save', () => {
        cy.get(entityEditButtonSelector).first().click();
        cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bAlt');
        cy.get(entityCreateSaveButtonSelector).click();
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bAltPageUrlPattern);
      });

      it('last delete button click should delete instance of MarketSegmentationTypeB2bAlt', () => {
        cy.intercept('GET', '/api/market-segmentation-type-b-2-b-alts/*').as('dialogDeleteRequest');
        cy.get(entityDeleteButtonSelector).last().click();
        cy.wait('@dialogDeleteRequest');
        cy.getEntityDeleteDialogHeading('marketSegmentationTypeB2bAlt').should('exist');
        cy.get(entityConfirmDeleteButtonSelector).click();
        cy.wait('@deleteEntityRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(204);
        });
        cy.wait('@entitiesRequest').then(({ response }) => {
          expect(response.statusCode).to.equal(200);
        });
        cy.url().should('match', marketSegmentationTypeB2bAltPageUrlPattern);

        marketSegmentationTypeB2bAlt = undefined;
      });
    });
  });

  describe('new MarketSegmentationTypeB2bAlt page', () => {
    beforeEach(() => {
      cy.visit(`${marketSegmentationTypeB2bAltPageUrl}`);
      cy.get(entityCreateButtonSelector).click();
      cy.getEntityCreateUpdateHeading('MarketSegmentationTypeB2bAlt');
    });

    it('should create an instance of MarketSegmentationTypeB2bAlt', () => {
      cy.get(`[data-cy="value"]`).type('plum Account Kids').should('have.value', 'plum Account Kids');

      cy.get(`[data-cy="description"]`)
        .type('../fake-data/blob/hipster.txt')
        .invoke('val')
        .should('match', new RegExp('../fake-data/blob/hipster.txt'));

      cy.get(`[data-cy="language"]`).select('ENGLISH');

      cy.get(entityCreateSaveButtonSelector).click();

      cy.wait('@postEntityRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(201);
        marketSegmentationTypeB2bAlt = response.body;
      });
      cy.wait('@entitiesRequest').then(({ response }) => {
        expect(response.statusCode).to.equal(200);
      });
      cy.url().should('match', marketSegmentationTypeB2bAltPageUrlPattern);
    });
  });
});
